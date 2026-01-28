# React Native

- React Native is a framework developed by Meta that allows developers to build cross-platform mobile applications using
  JavaScript and React, rendering real native UI components instead of web views.

| Feature  | React     | React Native |
|----------|-----------|--------------|
| Platform | Web       | Mobile       |
| UI Tags  | div, span | View, Text   |
| Styling  | CSS       | StyleSheet   |
| Output   | DOM       | Native UI    |
| Browser  | Required  | Not needed   |

```jsx
import { View, Text, Button } from "react-native";

export default function App() {
  return (
    <View>
      <Text>Hello React Native 👋</Text>
      <Button title="Click Me" onPress={() => alert("Clicked")} />
    </View>
  );
}
```

# Styling in react Native
```jsx
import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
  text: {
    fontSize: 20,
    color: "blue"
  }
});
```