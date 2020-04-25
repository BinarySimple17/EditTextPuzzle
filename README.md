# Word puzzle view library #

### Version ###

* [![](https://jitpack.io/v/BinarySimple17/EditTextPuzzle.svg)](https://jitpack.io/#BinarySimple17/EditTextPuzzle)

Looks like "T E _ T A B L E"

### How to get ###
* Step 1. Add it in your root build.gradle at the end of repositories:
``` gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
* Step 2. Add the dependency
``` gradle
	dependencies {
	        implementation 'com.github.BinarySimple17:EditTextPuzzle:version'
	}
```

### How to use ###
* Add to layout
``` xml
    <ru.binarysimple.edittextpuzzle.EditTextPuzzle
        android:id="@+id/puzzle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:placeholder="$"
        app:margin="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```
* Set text
``` java
        EditTextPuzzle editTextPuzzle = findViewById(R.id.puzzle);
        editTextPuzzle.setSecretText("testable");
        editTextPuzzle.setText("TE$TABLE");
```

* Check input
``` java
        findViewById(R.id.button).setOnClickListener(v -> {
            String result = "Wrong!";
            if (editTextPuzzle.checkInput()) {
                result = "Correct!";
            }
            Snackbar.make(findViewById(R.id.button), result, LENGTH_SHORT).show();
        });
```
