package com.linzh.android.newfriendvoice.ui.debug;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linzh.android.newfriendvoice.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linzh on 2018/3/25.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private List<Word> mWords;

    private OnItemClickListener mOnItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        @BindView(R.id.word_text)
        TextView mWordText;

        @BindView(R.id.code_text)
        TextView mCodeText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public WordAdapter(List<Word> words) {
        mWords = words;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Word word = mWords.get(position);
        holder.mWordText.setText(word.getKey());
        holder.mCodeText.setText(word.getValue());

        if (mOnItemClickListener != null) {
            holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(v, position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mWords != null && mWords.size() > 0) {
            return mWords.size();
        }
        return 1;
    }

    public String getItem(int position) {
        return mWords.get(position).getValue();
    }

    public Word getWord(int position) {
        return mWords.get(position);
    }

    public void addItem(Word word) {
        mWords.add(word);
        Collections.sort(mWords, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return Integer.parseInt(o1.getValue()) - Integer.parseInt(o2.getValue());
            }
        });
        notifyDataSetChanged();
    }

    public void removeItem(Word word) {
        mWords.remove(word);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mWords.remove(position);
        notifyDataSetChanged();
    }

    public List<Word> getAllWords() {
        return mWords;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
