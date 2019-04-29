import com.itmuch.lightsecurity.exception.LightSecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author oszhugc
 * @Date 2019\4\29 0029 23:07
 **/
@Slf4j
@ControllerAdvice
public class LightSecurityExceptionHandler {

    @ExceptionHandler(value = {LightSecurityException.class})
    @ResponseBody
    public ResponseEntity<String> error(LightSecurityException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
