package com.api.rest.encuestas.exception.handler;

import com.api.rest.encuestas.dto.ErrorDetail;
import com.api.rest.encuestas.dto.ValidationError;
import com.api.rest.encuestas.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice //controlador de las exceptions
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource; //inyeccion de la clase que permite definir mensajes de validacion personalizados definido en resources/messages.properties

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest httpServletRequest){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Recurso no encontrado");
        errorDetail.setDetail(exception.getClass().getName()); //obtenemos el nombre de la clase de la exception
        errorDetail.setDeveloperMessage(exception.getMessage());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }


    //MethodArgumentNotValidException es el error que da en consola luego de agregar validaciones de no dejar el campo vacio en Encuesta
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest){ //sin mensaje de validacion personalizado
    public @ResponseBody ErrorDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());

        //para obtener la ruta
        String requestPath = (String) httpServletRequest.getAttribute("javax.servlet.error.request_uri");

        if(requestPath == null){
            requestPath = httpServletRequest.getRequestURI(); //obtiene la URI que esta fallando
        }

        errorDetail.setTitle("Validacion Fallida");
        errorDetail.setDetail("La validacion de entrada falló"); //obtenemos el nombre de la clase de la exception
        errorDetail.setDeveloperMessage(exception.getMessage());

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors(); //obtener los errores en cada campo
        for(FieldError fieldError : fieldErrors){
            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fieldError.getField());

            //Si aun no hay errores en el array validationErrorList crea la lista al encontrar un error
            if(validationErrorList == null){
                validationErrorList = new ArrayList<ValidationError>();
                errorDetail.getErrors().put(fieldError.getField(), validationErrorList);
            }

            ValidationError validationError = new ValidationError();
            validationError.setCode(fieldError.getCode());
            //validationError.setMessage(fieldError.getDefaultMessage()); //Mensaje por defecto

            validationError.setMessage(messageSource.getMessage(fieldError, null)); //Mensaje personalizado definido en resources/messages.properties
            validationErrorList.add(validationError);

        }

        //return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST); //Mensaje por defecto
        return errorDetail; //mensaje personalizado
    }
}
