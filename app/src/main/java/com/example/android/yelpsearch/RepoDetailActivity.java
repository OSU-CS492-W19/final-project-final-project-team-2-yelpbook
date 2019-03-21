package com.example.android.yelpsearch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.yelpsearch.R;
import com.example.android.yelpsearch.data.YelpRest;
import com.example.android.yelpsearch.utils.YelpUtils;

public class RepoDetailActivity extends AppCompatActivity {
    private TextView mRepoNameTV;
    private TextView mRepoStarsTV;
    private TextView mRestAddressTV;
    private TextView mRestCityTV;

    private TextView mRestPhoneTV;

    private ImageView mRepoBookmarkIV;
    private ImageView mrestImgDetailIV;

    private YelpRestViewModel mYelpRestViewModel;
    private YelpRest mRepo;
    private boolean mIsSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);

        mRepoNameTV = findViewById(R.id.tv_repo_name);
        mRepoStarsTV = findViewById(R.id.tv_repo_stars);
        mRestAddressTV = findViewById(R.id.tv_rest_location_address);
        mRestCityTV =  findViewById(R.id.tv_rest_location_city);
        mRestPhoneTV = findViewById(R.id.tv_rest_phone);


        mRepoBookmarkIV = findViewById(R.id.iv_repo_bookmark);
        mrestImgDetailIV = findViewById(R.id.iv_rest_img_detail);

        mYelpRestViewModel = ViewModelProviders.of(this).get(YelpRestViewModel.class);

        mRepo = null;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(YelpUtils.EXTRA_YELP_REST)) {
            mRepo = (YelpRest) intent.getSerializableExtra(YelpUtils.EXTRA_YELP_REST);
            mRepoNameTV.setText(mRepo.name);
            mRepoStarsTV.setText("" + mRepo.rest_rating);
            mRestAddressTV.setText("Address: "+ mRepo.location_address  );
            mRestCityTV.setText("City: " + mRepo.location_city);
            
            mRestPhoneTV.setText("Phone: " + mRepo.rest_phone);

            Glide.with(mrestImgDetailIV.getContext())
                    .load(mRepo.img_url)
//                    .apply(new RequestOptions().override(720, 720))
                    .into(mrestImgDetailIV);

            mYelpRestViewModel.getYelpRestByName(mRepo.name).observe(this, new Observer<YelpRest>() {
                @Override
                public void onChanged(@Nullable YelpRest repo) {
                    if (repo != null) {
                        mIsSaved = true;
                        mRepoBookmarkIV.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    } else {
                        mIsSaved = false;
                        mRepoBookmarkIV.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    }
                }
            });
        }

        mRepoBookmarkIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRepo != null) {
                    if (!mIsSaved) {
                        mYelpRestViewModel.insertYelpRest(mRepo);
                    } else {
                        mYelpRestViewModel.deleteYelpRest(mRepo);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_on_web:
                viewRepoOnWeb();
                return true;
            case R.id.action_share:
                shareRepo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void viewRepoOnWeb() {
        if (mRepo != null) {
            Uri repoURI = Uri.parse(mRepo.html_url);
            Intent intent = new Intent(Intent.ACTION_VIEW, repoURI);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    public void shareRepo() {
        if (mRepo != null) {
            String shareText = getString(R.string.share_repo_text, mRepo.name, mRepo.html_url);
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(shareText)
                    .setChooserTitle(R.string.share_chooser_title)
                    .startChooser();
        }
    }
}
