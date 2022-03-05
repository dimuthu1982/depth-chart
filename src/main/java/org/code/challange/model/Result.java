package org.code.challange.model;

import java.util.ArrayList;
import java.util.List;

public class Result
{
    private List<String> results = new ArrayList();

    public Result() {}

    public Result(List<String> results)
    {
        this.results = results;
    }

    public Result(String result)
    {
        this.results.add(result);
    }

    public List<String> getResult()
    {
        return results;
    }

}
