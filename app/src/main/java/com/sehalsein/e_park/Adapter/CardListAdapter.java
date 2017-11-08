package com.sehalsein.e_park.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sehalsein.e_park.Model.CardDetail;
import com.sehalsein.e_park.R;

import java.util.List;

/**
 * Created by sehalsein on 08/11/17.
 */

public class CardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CardDetail> cardDetailList;


    public CardListAdapter() {
    }

    public CardListAdapter(Context context, List<CardDetail> cardDetailList) {
        this.context = context;
        this.cardDetailList = cardDetailList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_card_info, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CardDetail carDetail = cardDetailList.get(position);
        CardViewHolder h = (CardViewHolder) holder;
        h.cardNo.setText(censorNumber(carDetail.getCardNo()));
    }

    private String censorNumber(String no){

        return substring(0,4,no) + "-XXXX-XXXX-" +substring(12,16,no);
    }

    private String substring(int i,int j,String num){

        return num.substring(i,j);
    }
    @Override
    public int getItemCount() {
        return cardDetailList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        TextView cardNo;
        public CardViewHolder(View itemView) {
            super(itemView);

            cardNo = itemView.findViewById(R.id.card_number_text_view);
        }
    }
}
