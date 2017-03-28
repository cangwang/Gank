package material.com.news.model;

import java.util.List;

/**
 * Created by zjl on 2017/3/28.
 */

public class NewsEntity {
    private boolean error;
    private List<NewsItem> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<NewsItem> getResults() {
        return results;
    }

    public void setResults(List<NewsItem> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
