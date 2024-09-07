<img src="https://github.com/user-attachments/assets/ddeb2384-622b-4ada-8d3e-d1e8a1f5d6e6" alt="Material X Logo" width="250" height="300">

# Material X

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Version](https://img.shields.io/badge/version-1.0.4-green.svg)](https://github.com/PrathameshKumbhar291113/MaterialX/releases)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://jitpack.io/#PrathameshKumbhar291113/MaterialX)

**Material X** is an Android library that simplifies the creation of customizable Material Design components, such as dialog boxes, time pickers, date pickers, and date range pickers. The library is designed to be highly flexible, allowing you to tailor its components to fit the specific needs of your application.

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

Material X is available via [JitPack](https://jitpack.io/). To include it in your project:

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
        implementation 'com.github.PrathameshKumbhar291113:MaterialX:1.0.4'
    }
    ```

## Usage

### 1. Material Date Picker

Material X provides an easy-to-use date picker. Here’s how you can use it:

#### Material X Date Picker

```kotlin
import com.prathameshkumbhar.materialx.MaterialXDatePicker

MaterialXDatePicker().showMaterialXDatePicker(
                lifecycleOwner = this,
                isCancelable = true,
                onDateSelected = { selectedDate ->
                    // Handle the selected date
                    Log.d("DatePicker", "Selected date: $selectedDate")
                    Toast.makeText(this,
                        "Date Selected: $selectedDate",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onError = { error ->
                    Toast.makeText(this,
                        "Error Occurred: $error",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                customizations = { customization ->
                    customization.setTheme(R.style.ThemeMaterialCalendar)
                }
            )
```

### 2. Material Date Range Picker

Material X allows users to select a range of dates easily:

#### Material X Date Range Picker

```kotlin
import com.prathameshkumbhar.materialx.MaterialXDateRangePicker

  MaterialXDateRangePicker().showMaterialXDateRangePicker(
                lifecycleOwner = this,
                isCancelable = true,
                onDateRangeSelected = { selectedDateRange ->
                    // Handle the selected date range
                    Log.d("DatePicker",
                        "Selected first date: ${selectedDateRange.first} --- second date: ${selectedDateRange.second}"
                    )
                    Toast.makeText(this,
                        "Selected first date: ${selectedDateRange.first} --- second date: ${selectedDateRange.second}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onError = { error ->
                    Toast.makeText(this, 
                        "Error Occurred: $error", 
                        Toast.LENGTH_SHORT
                    ).show()
                },
                customizations = { customization ->
                    customization.setTheme(R.style.ThemeMaterialCalendar)
                }
            )
```

### 3. Material Time Picker

Integrate a Material-styled time picker into your app:

#### Material X Time Picker

```kotlin
import com.prathameshkumbhar.materialx.MaterialXTimePicker

MaterialXTimePicker().showMaterialXTimePicker(
                lifecycleOwner = this,
                isCancelable = true,
                is24HourFormat = false,
                onTimeSelected = { hour, minute->
                    // Handle the selected time
                    Log.d("DatePicker", "Selected Time  H: $hour  M: $minute")
                    Toast.makeText(this,
                        "Selected Time  H: $hour  M: $minute",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onError = { error ->
                    Toast.makeText(this,
                        "Error Occurred: $error",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                customizations = { customization ->
                    customization.setTheme(R.style.ThemeMaterialTimePicker)
                }
            )
```

### 4. Custom Dialog Box

Material X lets you create highly customizable dialog boxes. Here’s how:

#### Basic Dialog with Title and Description

```kotlin
import com.github.prathameshkumbhar.materialx.MaterialXCustomDialogBuilder

val customDialog = MaterialXCustomDialogs.MaterialXCustomDialogBuilder(context = this)
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

val customDialog = MaterialXCustomDialogs.MaterialXCustomDialogBuilder(context = this)
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

val customDialog = MaterialXCustomDialogs.MaterialXCustomDialogBuilder(context = this)
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

val customDialog = MaterialXCustomDialogs.MaterialXCustomDialogBuilder(context = this)
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

Material X provides a broad range of customization options:

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

## Screen Shots Of Demo App.

<img src="https://github.com/user-attachments/assets/467857dc-1d52-4fd4-a70f-91fa6b48908d" alt="Material X Logo" width="214" height="463">  <img src="https://github.com/user-attachments/assets/3365e14d-8f5a-4344-804b-c3d2686c625e" alt="Material X Logo" width="214" height="463">  <img src="https://github.com/user-attachments/assets/337abdb8-a7f6-4b60-94f7-14847d217c97" alt="Material X Logo" width="214" height="463"> 

<img src="https://github.com/user-attachments/assets/0b3509e6-cf62-4fdf-9d3b-b0920aa4a26a" alt="Material X Logo" width="214" height="463"> <img src="https://github.com/user-attachments/assets/45bb6cf8-3b09-4b17-94a0-85bb51ca9e03" alt="Material X Logo" width="214" height="463"> <img src="https://github.com/user-attachments/assets/2717a15d-bf83-4233-bf11-61628d8cbe41" alt="Material X Logo" width="214" height="463">



## Contributing

Contributions are welcome! Here's how you can contribute:

1. **Fork the Repository**: Click the "Fork" button at the top of this repository.
2. **Create a Branch**: Use `git checkout -b feature/YourFeatureName` to create a new branch.
3. **Commit Your Changes**: Make your changes and use `git commit -m 'Add some feature'` to commit your changes.
4. **Push to the Branch**: Use `git push origin feature/YourFeatureName` to push your changes to the branch.
5. **Open a Pull Request**: Open a pull request on GitHub and describe your changes.

Please ensure your code adheres to the project's coding standards and passes all tests.

## License

Material X is licensed under the Apache License, Version 2.0. You can view the full license [here](LICENSE).

## Acknowledgments

- Inspired by Google's Material Design guidelines.
- Thanks to the Android community for their support and contributions.

## Contact

For questions, feedback, or contributions, feel free to reach out via GitHub issues or email.

---

Thank you for using MaterialX! If you find this library helpful, please consider starring the repository and sharing it with others.
```

This `README.md` includes detailed instructions for using each feature of the `Material X` library, with code snippets and customization options for the Date Picker, Date Range Picker, Time Picker, and Customizable Dialog Box.
