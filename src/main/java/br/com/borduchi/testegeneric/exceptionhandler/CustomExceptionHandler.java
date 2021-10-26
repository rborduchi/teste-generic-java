package br.com.borduchi.testegeneric.exceptionhandler;

import br.com.borduchi.testegeneric.exceptionhandler.http_message_not_readable.HttpMessageNotReadableValidator;
import org.hibernate.QueryException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;
import java.sql.SQLException;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERRO_CONTATE_ADMINISTRADOR = "HOUVE UM PROBLEMA NA BASE DE DADOS. CONTATE UM ADMINISTRADOR OU TENTE NOVAMENTE MAIS TARDE.";
    private static final String TYPE_MISMATCH_TEXT = "TIPO DE PARÂMETRO NÃO CORRESPONDE AO ESPERADO.";
    private static final String NUMERIC_OVERFLOW = "NÃO É POSSÍVEL REALIZAR ESSA OPERAÇÃO, VALOR ENVIADO EXCEDE O LIMITE DE CARACTERES PERMITIDO.";
    private static final String NUMERIC_OVERFLOW_MATCHER = "ERROR: numeric field overflow";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpMessageNotReadableValidator httpMessageNotReadableValidator;

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String parameter = ex.getParameterName();
        String paramMessage;
        try {
            paramMessage = messageSource.getMessage(parameter, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException exception) {
            logger.error("O PARÂMETRO " + parameter.toUpperCase() + " NÃO ESTÁ MAPEADO NO MESSAGE.PROPERTIES");
            return new ResponseEntity<>(new ResponseError("OCORREU UM ERRO"), INTERNAL_SERVER_ERROR);
        }
        String exceptionMessage = "O PARÂMETRO " + paramMessage + " NÃO ESTÁ PRESENTE NA REQUISIÇÃO";
        return new ResponseEntity<>(new ResponseError(exceptionMessage.toUpperCase()), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ResponseError(TYPE_MISMATCH_TEXT.toUpperCase()), BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ResponseError handle(NullPointerException ex) {
        ex.printStackTrace();
        return new ResponseError("HOUVE UM ERRO NO PROCESSAMENTO DESSA OPERAÇÃO, CONTATE O ADMINISTRADOR");
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(EntityExistsException.class)
    public ResponseError handle(EntityExistsException ex) {
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseError handle(IllegalArgumentException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseError handle(IllegalStateException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseError handle(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        String specificalMessage = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        return nonNull(specificalMessage) && specificalMessage.contains(NUMERIC_OVERFLOW_MATCHER) ?
                new ResponseError(NUMERIC_OVERFLOW) :
                new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(QueryException.class)
    public ResponseError handle(QueryException ex) {
        return new ResponseError(ERRO_CONTATE_ADMINISTRADOR);
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ResponseError handle(SQLException e) {
        return new ResponseError(ERRO_CONTATE_ADMINISTRADOR);
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLGrammarException.class)
    public ResponseError handle(SQLGrammarException e) {
        return new ResponseError(ERRO_CONTATE_ADMINISTRADOR);
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseError handle(UnsupportedOperationException e) {
        return new ResponseError(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RollbackException.class)
    public ResponseError handle(RollbackException e) {
        e.printStackTrace();
        return new ResponseError(e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensagemFinal = nonNull(ex.getMessage()) ?
                httpMessageNotReadableValidator.validar(ex.getMessage()) :
                messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        ResponseError erros = new ResponseError(mensagemFinal);
        return handleExceptionInternal(ex, erros, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String mensagem = messageSource.getMessage(Objects.requireNonNull(fieldError), LocaleContextHolder.getLocale());
        ResponseError erro = new ResponseError(mensagem.toUpperCase());
        return handleExceptionInternal(ex, erro, headers, BAD_REQUEST, request);
    }

}
