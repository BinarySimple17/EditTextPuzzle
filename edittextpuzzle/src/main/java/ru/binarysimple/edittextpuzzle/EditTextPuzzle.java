package ru.binarysimple.edittextpuzzle;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputFilter;
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

import java.util.ArrayList;
import java.util.Arrays;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class EditTextPuzzle extends LinearLayout {

    private final Context context;
    private String placeHolder;
    private String text;
    private LinearLayout root;
    private String secretText;
    private int marginH;
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
            marginH = a.getDimensionPixelSize(R.styleable.EditTextPuzzle_margin, 0);
        } catch (Exception e) {
            placeHolder = "$";
            text = "";
        } finally {
            a.recycle();
            if (text == null) {
                text = "";
            }
            if (placeHolder == null) {
                placeHolder = "$";
            }
            text = text.toUpperCase();
        }
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public void setText(String text) {
        this.text = text.toUpperCase();
        parseText(this.text);
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
        }
        return result.toString().toUpperCase();
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
            result.setTextAppearance(context, R.style.TextEditPrimary);
        } else {
            result.setTextAppearance(R.style.TextEditPrimary);
        }
//        result.max

        // Apply the filters to control the input (alphanumeric)
        ArrayList<InputFilter> curInputFilters = new ArrayList<InputFilter>(Arrays.asList(result.getFilters()));
        curInputFilters.add(0, new InputFilter.AllCaps());
        curInputFilters.add(1, new InputFilter.LengthFilter(1));
        InputFilter[] newInputFilters = curInputFilters.toArray(new InputFilter[curInputFilters.size()]);
        result.setFilters(newInputFilters);

        result.setEms(2);
        result.setGravity(Gravity.CENTER_HORIZONTAL);
        result.setLayoutParams(createLayoutParams());
        result.setId(id);
        result.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        return result;
    }

    private LinearLayout.LayoutParams createLayoutParams() {
        LinearLayout.LayoutParams lp = new LayoutParams(
                WRAP_CONTENT,
//                getResources().getDimensionPixelSize(R.dimen.letter_normal),
                WRAP_CONTENT);
        lp.setMargins(0, 0, marginH, 0);
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

    public void setSecretText(String secretText) {
        this.secretText = secretText.toUpperCase();
    }

    public boolean checkInput() {
        return getInputText().equals(secretText);
    }
}
