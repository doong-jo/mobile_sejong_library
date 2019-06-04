package com.fourB.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.fourB.library.Barcode.BarcodeLinkActivity;
import com.fourB.library.Barcode.CustomScannerActivity;
import com.fourB.library.Anouncement.AnouncementActivity;
import com.fourB.library.MyPage.AppointmentBookActivity;
import com.fourB.library.MyPage.LendBookActivity;
import com.fourB.library.MyPage.LendBookRecordActivity;
import com.fourB.library.Report.ReportDialogActivity;
import com.fourB.library.Ebook.EbookActivity;
import com.fourB.library.GuideAll.GuideActivity;
import com.fourB.library.ChatBot.ChatBotActivity;
import com.fourB.library.ReadingRoom.ReadingRoomActivity;
import com.fourB.library.SearchBook.SearchBookActivity;
import com.fourB.library.StudyRoom.StudyRoomActivity;
import com.fourB.library.Widgets.MovableFloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CardView mEbookView;
    CardView mAnouncementView;
    CardView mBarcodeView;

    CardView mDeclareView;

    Slider banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        CardView readingRoom = (CardView)findViewById(R.id.cardView_reading_room);
        readingRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReadingRoomActivity.class);
                startActivity(intent);
            }
        });

        CardView searchBook = (CardView)findViewById(R.id.cardView_search_book);
        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchBookActivity.class);
                startActivity(intent);
            }
        });

        MovableFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
                startActivity(intent);
            }
        });

        CoordinatorLayout.LayoutParams lp  = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        fab.setCoordinatorLayout(lp);

        CardView studyRoomCardView = (CardView) findViewById(R.id.studyroom_cardview);
        studyRoomCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudyRoomActivity.class);
                startActivity(intent);
            }
        });

        CardView guideCardView = (CardView) findViewById(R.id.guide_cardview);
        guideCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(intent);
            }
        });

        CardView mobileCardView = (CardView) findViewById(R.id.MobileCardView);
        mobileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        mEbookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EbookActivity.class);
                startActivity(intent);
            }
         });

        mAnouncementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnouncementActivity.class);
                startActivity(intent);
            }
        });

        mBarcodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                    integrator.setCaptureActivity(CustomScannerActivity.class);
                    integrator.setOrientationLocked(false);
                    integrator.initiateScan();
            }
        });

        Slider.init(new PicassoImageLoadingService());
        banner.setAdapter(new MainSliderAdapter());
        banner.setSelectedSlide(2);

        mDeclareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDialogActivity DeclareDialog = new ReportDialogActivity(MainActivity.this);

                DeclareDialog.callFunction();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void show() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_mobilecard);

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String title = getIntent().getStringExtra("title");
        if( title != null ) {
            final String body = getIntent().getStringExtra("body");
            final String type = getIntent().getStringExtra("type");
            final String content = getIntent().getStringExtra("content");
            final String date = getIntent().getStringExtra("date");

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            builder.setMessage(body + '\n' + "시간 : " + date + '\n' + "사유 : " + type + '\n' + "접수내용 : " + content);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            builder.setCancelable(true);
            builder.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanResult == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                if(scanResult.getContents().equals(getString(R.string.barcode_bookBarcode_01)) || scanResult.getContents().equals(getString(R.string.barcode_bookBarcode_02))){
                    Intent newIntent = new Intent(getApplicationContext(), BarcodeLinkActivity.class);
                    newIntent.putExtra("BarcodeScanNumber", scanResult.getContents());
                    startActivity(newIntent);
                }else {
                    Toast.makeText(this, "등록되지 않은 도서입니다!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }

    }


    private void initView() {
        banner = (Slider) findViewById(R.id.banner_main);
        mEbookView = (CardView)findViewById(R.id.EbookView);
        mAnouncementView = (CardView)findViewById(R.id.AnouncementView);
        mBarcodeView = (CardView) findViewById(R.id.barcode_cardView);
        mDeclareView = (CardView) findViewById(R.id.declare_cardview);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lend_book) {

            Intent intent = new Intent(getApplicationContext(), LendBookActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_lend_book_record) {
            Intent intent = new Intent(getApplicationContext(), LendBookRecordActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_appointment_book) {
            Intent intent = new Intent(getApplicationContext(), AppointmentBookActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MainSliderAdapter extends SliderAdapter {

        @Override
        public int getItemCount() {
            return 3;
        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            switch (position) {
                case 0:
                    viewHolder.bindImageSlide(R.drawable.banner_1);
                    break;
                case 1:
                    viewHolder.bindImageSlide(R.drawable.banner_2);
                    break;
                case 2:
                    viewHolder.bindImageSlide(R.drawable.banner_4);
                    break;
            }
        }
    }
    public class PicassoImageLoadingService implements ImageLoadingService {

        @Override
        public void loadImage(String url, ImageView imageView) {
            //Picasso.with(context).load(imageUrl).resize(30, 30).into(imageView); }
            Picasso.get().load(url).into(imageView);
        }

        @Override
        public void loadImage(int resource, ImageView imageView) {
            Picasso.get().load(resource).into(imageView);
        }

        @Override
        public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
            Picasso.get().load(url).placeholder(placeHolder).error(errorDrawable).into(imageView);
        }
    }
}
