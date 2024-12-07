//
//  ContentViewUi.swift
//  iosEcommerceBeatiful
//
//  Created by kenjimaeda on 20/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//


import XCTest

final class ContentViewUi {
    private var app: XCUIApplication
    
    init(app: XCUIApplication) {
        self.app = app
    }
    
    public var taskListCategoriesHome:  XCUIElement {
        let predicate  = NSPredicate(format: "identifier == '\(acesssibility_List_Categories)'")
        return app.descendants(matching: .any).matching(predicate).firstMatch
    }
    
    public var taskStaticTextCategory:  XCUIElement {
        app.staticTexts["Limpeza"]
    }
    
    public var taskHorizontalCategory:  XCUIElement {
        app.collectionViews[acessibility_Horizontal_Categories]
    }
    
    public var taskTextField: XCUIElement {
        app.textFields[acessibility_Label_Text_Field]
    }
    
    public var taskButtonHoriontalCategory: XCUIElement {
        return app.cells.containing(.staticText, identifier: accesibility_Label_button_horizontal).firstMatch
    }
    
    public var identifierLabelRowItemCard: XCUIElement {
        return app.cells.containing(.staticText, identifier: accesibility_Label_button_horizontal).element(boundBy: 1)
    }
    
}
