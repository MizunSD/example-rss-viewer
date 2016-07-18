package ru.teaz.examplerssviewer.data.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.teaz.examplerssviewer.data.NewsSource;
import rx.Observable;

public class RestClient {

    final Map<NewsSource, ServerApi> mData;

    public RestClient() {
        mData = new HashMap<>();
        for (NewsSource source: NewsSource.values()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(source.getUrl())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
            mData.put(source, retrofit.create(ServerApi.class));
        }
    }

    @Nullable
    public Observable<XmlRss> getRss(@NonNull NewsSource source) {
        ServerApi serverApi = mData.get(source);
        if (serverApi != null) {
            return serverApi.getRss(source.getPath());
        }
        return null;
    }
}
