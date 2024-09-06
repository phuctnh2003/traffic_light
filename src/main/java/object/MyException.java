package object;

public class MyException extends Exception {
    private object.Error error;

    public MyException(int code, String message) {
        this.error = new object.Error(code, message);
    }

    public object.Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
