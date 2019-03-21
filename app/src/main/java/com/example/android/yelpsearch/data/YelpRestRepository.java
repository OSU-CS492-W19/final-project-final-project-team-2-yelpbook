package com.example.android.yelpsearch.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class YelpRestRepository {
    private YelpRestDao mYelpRestDao;

    public YelpRestRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mYelpRestDao = db.YelpRestDao();
    }

    public void insertYelpRest(YelpRest restaurant) {
        new InsertAsyncTask(mYelpRestDao).execute(restaurant);
    }

    public void deleteYelpRest(YelpRest restaurant) {
        new DeleteAsyncTask(mYelpRestDao).execute(restaurant);
    }

    public LiveData<List<YelpRest>> getAllYelpRests() {
        return mYelpRestDao.getAllRepos();
    }

    public LiveData<YelpRest> getYelpRestByName(String fullName) {
        return mYelpRestDao.getRepoByName(fullName);
    }

    private static class InsertAsyncTask extends AsyncTask<YelpRest, Void, Void> {
        private YelpRestDao mAsyncTaskDao;
        InsertAsyncTask(YelpRestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(YelpRest... YelpRests) {
            mAsyncTaskDao.insert(YelpRests[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<YelpRest, Void, Void> {
        private YelpRestDao mAsyncTaskDao;
        DeleteAsyncTask(YelpRestDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(YelpRest... YelpRests) {
            mAsyncTaskDao.delete(YelpRests[0]);
            return null;
        }
    }
}
