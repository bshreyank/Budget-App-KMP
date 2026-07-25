# Fix NullPointerException in Navigation Bar

The application crashes with a `NullPointerException` when attempting to access `screen.icon` in the `App.kt` file. This is caused by an initialization order issue in the `Screen` sealed class, where the `entries` list in the companion object may contain null references if accessed during class initialization.

## User Review Required

> [!IMPORTANT]
> This change modifies how the list of screens is initialized. Instead of a direct list, it will now be a `lazy` property to ensure all screen objects are fully initialized before use.

## Proposed Changes

### [composeApp]

#### [MODIFY] [Screen.kt](file:///D:/Android_Development/Android_Projects_2/BudgetAppKMP/composeApp/src/commonMain/kotlin/com/shreyank/budgetappkmp/Screen.kt)

- Change `val entries` in the `companion object` to be a `lazy` property.

## Verification Plan

### Automated Tests
- I will verify that the code compiles. Since this is a runtime initialization issue, manual verification on a device/emulator is the most reliable way to confirm the fix.

### Manual Verification
- Run the application and verify that the navigation bar renders correctly without crashing.
- Verify that switching between tabs works as expected.
