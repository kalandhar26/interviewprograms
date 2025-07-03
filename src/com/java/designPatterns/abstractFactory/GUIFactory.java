package com.java.designPatterns.abstractFactory;

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}