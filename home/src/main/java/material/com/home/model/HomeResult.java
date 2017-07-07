package material.com.home.model;

/**
 * Created by Cangwang on 2017/6/14.
 */

public class HomeResult<T> {
    public boolean error;
    //@SerializedName(value = "results", alternate = {"result"})
    public T results;
}
