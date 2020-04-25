package ru.binarysimple.edittextpuzzle;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class EditTextPuzzle extends LinearLayout {

    private final Context context;
    private String placeHolder;
    private String text;
    private LinearLayout root;
    private int padTop;
    private int padBot;
    private int padLeft;
    private int padRight;

    public EditTextPuzzle(Context context) {
        super(context);
        getAttrs(context, null);
        inflate(context, (ViewGroup) this.getParent());
        this.context = context;
    }

    public EditTextPuzzle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        inflate(context, (ViewGroup) this.getParent());
        this.context = context;
    }

    public EditTextPuzzle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
        inflate(context, (ViewGroup) this.getParent());
        this.context = context;
    }

    private void getAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EditTextPuzzle,
                0, 0);
        try {
            placeHolder = a.getString(R.styleable.EditTextPuzzle_placeholder);
            text = a.getString(R.styleable.EditTextPuzzle_text);
        } catch (Exception e) {
            placeHolder = "$";
            text = "";
        } finally {
            a.recycle();
        }
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public void setText(String text) {
        this.text = text;
        parseText(text);
    }

    public String getInputText() {
        StringBuilder result = new StringBuilder();
        if (root == null) {
            return result.toString();
        }

        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof TextView) {
                result = result.append(((TextView) view).getText());
            }
            if (view instanceof EditText) {
                result.append(((TextView) view).getText());
            }
        }
        return result.toString();
    }

    private void parseText(String text) {
        if (root == null) {
            return;
        }

        for (int i = 0; i < text.length(); i++) {
            if (placeHolder != null && placeHolder.charAt(0) == text.charAt(i)) {
                root.addView(createEditText(i + 17));
            } else {
                root.addView(createTextView(i + 20, String.valueOf(text.charAt(i))));
            }
        }

        root.invalidate();
    }

    private void inflate(Context context, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.edit_text_puzzle_view, this);

        root = findViewById(R.id.puzzle_root);
    }

    private EditText createEditText(int id) {
        EditText result = new EditText(context);
        if (Build.VERSION.SDK_INT < 23) {
            result.setTextAppearance(context, R.style.TextViewPrimary);
        } else {
            result.setTextAppearance(R.style.TextViewPrimary);
        }
        result.setLayoutParams(createLayoutParams());
        result.setId(id);
        result.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        return result;
    }

    private LinearLayout.LayoutParams createLayoutParams() {
        LinearLayout.LayoutParams lp = new LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.letter_normal),
                LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        lp.gravity = Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM;
        return lp;
    }

    private TextView createTextView(int id, String text) {
        TextView result = new TextView(context);
        if (Build.VERSION.SDK_INT < 23) {
            result.setTextAppearance(context, R.style.TextViewPrimary);
        } else {
            result.setTextAppearance(R.style.TextViewPrimary);
        }
        result.setId(id);
        result.setLayoutParams(createLayoutParams());
        result.setText(text);
        return result;
    }
}
