//
//  HomeScreen_UiTests.swift
//  iosEcommerceBeatiful
//
//  Created by kenjimaeda on 20/11/24.
//  Copyright © 2024 orgName. All rights reserved.
//

@testable import iosEcommerceBeatiful
import XCTest

final class HomeScreen_UiTests: XCTestCase {
    private var app: XCUIApplication!
    private var UIs: ContentViewUi!
    
    
    
    override func  setUpWithError() throws {
        app = XCUIApplication()
        UIs = ContentViewUi(app: app)
        continueAfterFailure  = false
        app.launch()
    }
    
    
    override func tearDownWithError() throws {
        app = nil
    }
    
    
    func testeShouldShowsTheCategoriesCorrectly() {
        let item = UIs.taskListCategoriesHome
        XCTAssertTrue(item.waitForExistence(timeout: 2))
        
        
        XCTAssertEqual(item.cells.count, 6)
        XCTAssertTrue(UIs.taskStaticTextCategory.exists)
    }
    
    func testeShouldShowTheHorizontalCategoriesCorrectly() {
        let item = UIs.taskHorizontalCategory
        XCTAssertTrue(item.waitForExistence(timeout: 2))
        
        XCTAssertEqual(item.cells.count, 1)
        XCTAssertTrue(UIs.taskStaticTextCategory.exists)
    }
    
    func testShouldThevalueOfTextFieldIsTheSameAsOneEntered() {
        let textField = UIs.taskTextField
        XCTAssertTrue(textField.exists)
        textField.tap()
        textField.typeText("Mochila")
        XCTAssertEqual(textField.value as! String, "Mochila")
    }
    
    
    //para testar scroll automatico
    //o titulo vai estar escccondido por iisso primerio garanto com
    // XCTAssertFalse(title.waitForExistence(timeout: 2)) so apos scrollar que vai existir por isso
    // XCTAssertTrue(title.waitForExistence(timeout: 2))
    //dai havia tanto eletronicos na list horizontal acima como na lista vertical
    //para caputarar os elementos como mesmo staticText pode usar tecnica abaixo
    //public var identifierLabelRowItemCard: XCUIElement {
    //    return app.cells.containing(.staticText, identifier: accesibility_Label_button_horizontal).element(boundBy: 1)
    //}
    func testShouldWhenPressButtonHaveNavigateToCorrectIndex() {
        let title = UIs.identifierLabelRowItemCard
        XCTAssertFalse(title.waitForExistence(timeout: 2))
        let button  = UIs.taskButtonHoriontalCategory
        XCTAssertTrue(button.waitForExistence(timeout: 2))
        button.tap()
        XCTAssertTrue(title.waitForExistence(timeout: 2))
        
    }
    
    
    
}

