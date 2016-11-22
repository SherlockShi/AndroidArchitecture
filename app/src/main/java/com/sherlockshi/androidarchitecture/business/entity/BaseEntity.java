package com.sherlockshi.androidarchitecture.business.entity;

/**
 * Author: SherlockShi
 * Date:   2016-11-15 22:33
 * Description:
 */
public class BaseEntity<T> {

    /**
     * error : false
     * results : null
     */

    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
