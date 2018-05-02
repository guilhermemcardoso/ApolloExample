package cardoso.guilherme.graphql;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import cardoso.guilherme.graphql.api.CreateLinkMutation;

/**
 * Created by guilherme on 02/05/18.
 */

public class NewLinkService extends Service {

    public final static String BASE_URL = "http://172.16.0.83:3000/graphql";
    private ApolloClient apolloClient;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        apolloClient = ApolloClient
                .builder()
                .serverUrl(BASE_URL)
                .build();

        //apolloClient.subscribe();

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
