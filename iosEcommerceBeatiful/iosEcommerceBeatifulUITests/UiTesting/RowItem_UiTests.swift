//
//  RowItem_UiTests.swift
//  iosEcommerceBeatiful
//
//  Created by kenjimaeda on 17/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import XCTest
@testable import iosEcommerceBeatiful


final class RowItem_UiTests: XCTestCase {
    private var app: XCUIApplication!
    private var UIs: ContentViewUi!
   
    override func setUpWithError() throws {
        app = XCUIApplication()
        UIs = ContentViewUi(app: app)
        continueAfterFailure = false
        app.launchEnvironment = ["ENV": "TEST"]
        app.launch()
    }
    
    override func tearDownWithError() throws {
        app = nil
        
    }
    
    func testIfTitleIsDisplayed()  {
        let title = UIs.taskTextTitleRowItemCard
        XCTAssert(title.waitForExistence(timeout: 2))
    }
    
    func testIfRenderCorrectlyImageWhenProductImageIsEmpty()  {
        let identifier = UIs.identifierImageIsEmpty
        XCTAssert(identifier.waitForExistence(timeout: 2))
    }
    
}
