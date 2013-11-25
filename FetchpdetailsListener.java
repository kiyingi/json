package com.news.ialert;

import java.util.List;

public interface FetchpdetailsListener {
public void onFetchComplete(List<pdetails> data);
public void onFetchFailure(String msg);
}

