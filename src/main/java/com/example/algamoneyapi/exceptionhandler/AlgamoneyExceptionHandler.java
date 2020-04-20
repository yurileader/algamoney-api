package com.example.algamoneyapi.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice //Observa toda a aplicaçao e deixa disponivel para os controllers
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	
	//Trata as mensagens de erro do Spring
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
		    HttpHeaders headers, HttpStatus status, WebRequest request) {

		  String mensagemUsuario = this.messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		  String mensagemDesenvolvedor = Optional.ofNullable(ex.getCause()).orElse(ex).toString();

		  List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));

		  return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
		}
	
	/* Usado quando a validação de um argumento não é valido*/
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> erros = criarListaDeErros(ex.getBindingResult());
		
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	//Trata excecoes de recurso nao encontrado
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	@ResponseStatus()
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
		
		List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}
	
	@ExceptionHandler( {DataIntegrityViolationException.class } )
	public ResponseEntity<Object> handleDataIntegrityViolationException (DataIntegrityViolationException ex, WebRequest request){
	
		String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);	

		List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

	}
	
	/*Metodo criado para retonar uma lista de erros
	  Qualquer campo obrigatorio preenchido errado, eles irão aparecer nesa lista*/
	private List<Error> criarListaDeErros(BindingResult bindingResult){
		List<Error> erros = new ArrayList<>();
		
		for(FieldError fieldError : bindingResult.getFieldErrors()) {

			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();

			erros.add(new Error(mensagemUsuario, mensagemDesenvolvedor));
		}
		
		return erros;
	}
	
	
	
	public static class Error {
		
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public Error(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
	}
}
