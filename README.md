# Material X

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Version](https://img.shields.io/badge/version-1.0.2-green.svg)](https://github.com/PrathameshKumbhar291113/MaterialX/releases)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://jitpack.io/#PrathameshKumbhar291113/MaterialX)

**MaterialX** is an Android library that simplifies the creation of customizable Material Design components, such as dialog boxes, time pickers, date pickers, and date range pickers. The library is designed to be highly flexible, allowing you to tailor its components to fit the specific needs of your application.

## Features

- **Customizable Dialog Boxes**
  - Background customization (color, drawable, or image).
  - Customizable title, description, and buttons.
  - Support for one or two buttons with custom text and backgrounds.
  - Image support above the title.
  - Custom corner radius and padding.
  - Font customization for title, description, and buttons.
  - Configurable cancellability.

- **Material Time Picker**
  - Easy integration with a modern, Material-styled time picker.
  - Support for both 12-hour and 24-hour formats.

- **Material Date Picker**
  - Simple and intuitive date selection.
  - Support for custom date formats and initial dates.

- **Material Date Range Picker**
  - Seamless selection of date ranges with a Material-styled UI.
  - Customizable start and end dates, as well as formatting.

## Getting Started

### Installation

MaterialX is available via [JitPack](https://jitpack.io/). To include it in your project:

1. Add the JitPack repository to your root `build.gradle` file:

    ```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    ```

2. Add the dependency to your module's `build.gradle` file:

    ```groovy
    dependencies {
        implementation 'com.github.PrathameshKumbhar291113:MaterialX:1.0.2'
    }
    ```

## Usage

### 1. Material Date Picker

MaterialX provides an easy-to-use date picker. Here’s how you can use it:

#### Basic Date Picker

```kotlin
import com.github.prathameshkumbhar.materialx.DatePicker

DatePicker.show(
    context = this,
    initialDate = LocalDate.now(),
    onDateSelected = { selectedDate ->
        // Handle the selected date
        Log.d("DatePicker", "Selected date: $selectedDate")
    }
)
```

#### Date Picker with Custom Initial Date

```kotlin
import com.github.prathameshkumbhar.materialx.DatePicker

DatePicker.show(
    context = this,
    initialDate = LocalDate.of(2023, 12, 25),
    onDateSelected = { selectedDate ->
        // Handle the selected date
        Log.d("DatePicker", "Selected date: $selectedDate")
    }
)
```

### 2. Material Date Range Picker

MaterialX allows users to select a range of dates easily:

#### Basic Date Range Picker

```kotlin
import com.github.prathameshkumbhar.materialx.DateRangePicker

DateRangePicker.show(
    context = this,
    initialStartDate = LocalDate.now(),
    initialEndDate = LocalDate.now().plusDays(7),
    onDateRangeSelected = { startDate, endDate ->
        // Handle the selected date range
        Log.d("DateRangePicker", "Selected range: $startDate to $endDate")
    }
)
```

#### Date Range Picker with Custom Range

```kotlin
import com.github.prathameshkumbhar.materialx.DateRangePicker

DateRangePicker.show(
    context = this,
    initialStartDate = LocalDate.of(2023, 1, 1),
    initialEndDate = LocalDate.of(2023, 12, 31),
    onDateRangeSelected = { startDate, endDate ->
        // Handle the selected date range
        Log.d("DateRangePicker", "Selected range: $startDate to $endDate")
    }
)
```

### 3. Material Time Picker

Integrate a Material-styled time picker into your app:

#### Basic Time Picker

```kotlin
import com.github.prathameshkumbhar.materialx.TimePicker

TimePicker.show(
    context = this,
    initialHour = 10,
    initialMinute = 30,
    is24HourView = true,
    onTimeSelected = { hourOfDay, minute ->
        // Handle the selected time
        Log.d("TimePicker", "Selected time: $hourOfDay:$minute")
    }
)
```

#### Time Picker with Custom Initial Time

```kotlin
import com.github.prathameshkumbhar.materialx.TimePicker

TimePicker.show(
    context = this,
    initialHour = 14,
    initialMinute = 45,
    is24HourView = false,
    onTimeSelected = { hourOfDay, minute ->
        // Handle the selected time in 12-hour format
        Log.d("TimePicker", "Selected time: $hourOfDay:$minute")
    }
)
```

### 4. Custom Dialog Box

MaterialX lets you create highly customizable dialog boxes. Here’s how:

#### Basic Dialog with Title and Description

```kotlin
import com.github.prathameshkumbhar.materialx.MaterialXCustomDialogBuilder

val customDialog = MaterialXCustomDialogBuilder(context)
    .setTitle("Welcome")
    .setDescription("Thank you for choosing MaterialX. Enjoy your experience!")
    .setPositiveButton("OK") { 
        // Positive button action
    }
    .setCancelable(true)
    .build()

customDialog.show()
```

#### Dialog with Custom Background and Fonts

```kotlin
import com.github.prathameshkumbhar.materialx.MaterialXCustomDialogBuilder

val customDialog = MaterialXCustomDialogBuilder(context)
    .setTitle("Custom Dialog")
    .setDescription("This is a dialog with a custom background and font.")
    .setPositiveButton("Accept")
    .setNegativeButton("Decline")
    .setBackgroundColor(ContextCompat.getColor(context, R.color.dialogBackgroundColor))
    .setTitleFont(ResourcesCompat.getFont(context, R.font.custom_title_font))
    .setDescriptionFont(ResourcesCompat.getFont(context, R.font.custom_description_font))
    .build()

customDialog.show()
```

#### Dialog with Image Above Title

```kotlin
import com.github.prathameshkumbhar.materialx.MaterialXCustomDialogBuilder

val customDialog = MaterialXCustomDialogBuilder(context)
    .setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_image))
    .setTitle("Image Dialog")
    .setDescription("This dialog has an image above the title.")
    .setPositiveButton("Got it!")
    .build()

customDialog.show()
```

#### Dialog with Custom Button Background and Corner Radius

```kotlin
import com.github.prathameshkumbhar.materialx.MaterialXCustomDialogBuilder

val customDialog = MaterialXCustomDialogBuilder(context)
    .setTitle("Styled Dialog")
    .setDescription("This dialog has a gradient button background and rounded corners.")
    .setPositiveButton("Agree")
    .setNegativeButton("Disagree")
    .setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_background))
    .setPadding(16, 16, 16, 16)
    .build()

customDialog.show()
```

## Customization Options

MaterialX provides a broad range of customization options:

- **Background**:
  - Set a solid color: `.setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundColor))`
  - Use a drawable as the background: `.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.backgroundDrawable))`
  - Apply an image as the background: `.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.backgroundImage))`

- **Text Customization**:
  - Customize the title and description text with different fonts: `.setTitleFont(customFont)` and `.setDescriptionFont(customFont)`

- **Buttons**:
  - Customize button text and background: `.setPositiveButton("OK", onClick)` and `.setNegativeButton("Cancel", onClick)`
  - Apply a drawable as the button background: `.setPositiveButtonDrawable(ContextCompat.getDrawable(context, R.drawable.buttonBackground))`

- **Corner Radius**:
  - Adjust the corner radius of the dialog: `.setCornerRadius(16f)`

- **Padding**:
  - Customize padding around the dialog content: `.setPadding(16, 16, 16, 16)`

## Contributing

Contributions are welcome! Here's how you can contribute:

1. **Fork the Repository**: Click the "Fork" button at the top of this repository.
2. **Create a Branch**: Use `git checkout -b feature/YourFeatureName` to create a new branch.
3. **Commit Your Changes**: Make your changes and use `git commit -m 'Add some feature'` to commit your changes.
4. **Push to the Branch**: Use `git push origin feature/YourFeatureName` to push your changes to the branch.
5

. **Open a Pull Request**: Open a pull request on GitHub and describe your changes.

Please ensure your code adheres to the project's coding standards and passes all tests.

## License

MaterialX is licensed under the Apache License, Version 2.0. You can view the full license [here](LICENSE).

## Acknowledgments

- Inspired by Google's Material Design guidelines.
- Thanks to the Android community for their support and contributions.

## Contact

For questions, feedback, or contributions, feel free to reach out via GitHub issues or email.

---

Thank you for using MaterialX! If you find this library helpful, please consider starring the repository and sharing it with others.
```

This `README.md` includes detailed instructions for using each feature of the `MaterialX` library, with code snippets and customization options for the Date Picker, Date Range Picker, Time Picker, and Customizable Dialog Box.