package com.news.ialert;

import java.util.List;

public interface FetchProgramListener {
public void onFetchComplete(List<programs> data);
public void onFetchFailure(String msg);
}

