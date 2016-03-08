package com.vhg.empire.merchant.MaFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vhg.empire.merchant.DividerItemDecoration;
import com.vhg.empire.merchant.MaAdapter.ExampleAdapter;
import com.vhg.empire.merchant.MaAdapter.models.ExampleModel;
import com.vhg.empire.merchant.R;
import com.vhg.empire.merchant.database.ProductBackend;
import com.vhg.empire.merchant.database.ProductDetailObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VinceGee on 10/19/2015.
 */
public class ProductFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private List<ExampleModel> mModels;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        setHasOptionsMenu(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        //get Adapter
        mModels = new ArrayList<>();

        ProductBackend productBackend = new ProductBackend(this.getActivity());

        ArrayList<ProductDetailObject> items =null; /*productBackend.dictionaryWords();*/

        for (ProductDetailObject goods : items) {
            mModels.add(new ExampleModel(goods));
        }

        mAdapter = new ExampleAdapter(getActivity(), mModels);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<ExampleModel> filteredModelList = filter(mModels, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<ExampleModel> filter(List<ExampleModel> models, String query) {
        query = query.toLowerCase();

        final List<ExampleModel> filteredModelList = new ArrayList<>();
        for (ExampleModel model : models) {
            final String text = model.getProductDetailObject().getP_name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
