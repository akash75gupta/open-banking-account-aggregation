package io.akash.openbanking.accountaggregator.helper.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.constant.ExceptionConstants;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.ErrorResponseDto;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.util.DateUtil;
import io.akash.openbanking.accountaggregator.helper.exception.CustomException;

@ControllerAdvice("io.akash.openbanking.accountaggregator.accountdiscovery.controller")
public class AccountManagementExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(AccountManagementExceptionHandler.class);

	@ExceptionHandler(CustomException.class)
	public final ResponseEntity<ErrorResponseDto> handleCustomExceptions(CustomException exception, WebRequest request) {
		log.info("Executing AccountDiscoveryExceptionHandler.handleCustomExceptions() with Param 1 -"
				+ "exception(ExceptionBase): {} and Param 2 - request(WebRequest): {}", exception, request);

		List<String> errorMessageList = new ArrayList<>(1);

		switch (exception.getStatus()) {
		case INTERNAL_SERVER_ERROR:
			errorMessageList.add(ExceptionConstants.INTERNAL_SERVER_ERROR_500_MESSAGE);
		default:
			errorMessageList.add(exception.getMessage());
		}
		ErrorResponseDto errorDetails = new ErrorResponseDto(DateUtil.getCurrentTimeStamp(), errorMessageList,
				request.getDescription(false));

		log.error("Custom Exception: {}", errorDetails);
		log.error("{}", exception);

		log.info(
				"Returning from AccountDiscoveryExceptionHandler.handleCustomExceptions() with results - "
						+ "errorDetails(ErrorDetails): {} and status(HttpStatus) :{}",
				errorDetails, exception.getStatus());

		return new ResponseEntity<>(errorDetails, exception.getStatus());
	}

	@Override
	public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders httpHeaders, HttpStatus status, WebRequest request) {
		log.info("Executing AccountDiscoveryExceptionHandler.handleMethodArgumentNotValid() with Param 1 -"
				+ "exception(ExceptionBase): {} and Param 2 - request(WebRequest): {}", exception, request);

		List<String> errorMessageList = new ArrayList<>(1);
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errorMessageList.add("Validation failed for field: " + fieldName + "; RCA:" + errorMessage);
		});

		ErrorResponseDto errorDetails = new ErrorResponseDto(DateUtil.getCurrentTimeStamp(), errorMessageList,
				request.getDescription(false));

		log.error("Validation Exception: {}", errorDetails);
		log.error("{}", exception);

		log.info("Returning from AccountDiscoveryExceptionHandler.handleMethodArgumentNotValid() with results - "
				+ "errorDetails(ErrorDetails): {} and status(HttpStatus) :{}", errorDetails, status);

		return new ResponseEntity<>(errorDetails, status);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponseDto> handleUnknownExceptions(Exception exception, WebRequest request) {
		log.info("Executing AccountDiscoveryExceptionHandler.handleGeneralExceptions() with Param 1 -"
				+ "exception(ExceptionBase): {} and Param 2 - request(WebRequest): {}", exception, request);

		List<String> errorMessageList = new ArrayList<>(1);
		errorMessageList.add(ExceptionConstants.INTERNAL_SERVER_ERROR_500_MESSAGE);

		ErrorResponseDto errorDetails = new ErrorResponseDto(DateUtil.getCurrentTimeStamp(), errorMessageList,
				request.getDescription(false));

		log.error("Unknown Exception: {}", errorDetails);
		log.error("{}", exception);

		log.info(
				"Returning from AccountDiscoveryExceptionHandler.handleGeneralExceptions() with results - "
						+ "errorDetails(ErrorDetails): {} and status(HttpStatus) :{}",
				errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
