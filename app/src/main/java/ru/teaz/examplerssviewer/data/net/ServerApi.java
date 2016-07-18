package ru.teaz.examplerssviewer.data.net;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ServerApi {

    @GET("/{path}")
    Observable<XmlRss> getRss(@Path("path") String path);
}
