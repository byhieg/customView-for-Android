package cn.byhieg.daydemo02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {


    public IndexView indexView;
    public ListView listView;
    public ContentAdapter adapter;
    public NameParser nameParser = new NameParser();
    public List<SortModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indexView = (IndexView) findViewById(R.id.index);


        listView = (ListView) findViewById(R.id.listView);
        adapter = new ContentAdapter(getApplicationContext());
        list = filledData(getResources().getStringArray(R.array.date));
        Collections.sort(list, new SpellComparator());
        adapter.setData(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),((SortModel)adapter.getItem(position))
                        .getName(),Toast.LENGTH_SHORT).show();
            }
        });

        indexView.setITextListener(new IndexView.ITextListener() {
            @Override
            public void onListener(Character s) {
                int pos = adapter.getPosForSelection(s);
                if (pos != -1) {
                    listView.setSelection(pos);
                }else{
                    Toast.makeText(getApplicationContext(),"没有数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private List<SortModel> filledData(String [] date){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.length; i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            String pinyin = nameParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
}
