package backend.overhere.exception;

//DB 예외
public class DataAccessException extends org.springframework.dao.DataAccessException {
    public DataAccessException(String msg) {
        super(msg);
    }
}
