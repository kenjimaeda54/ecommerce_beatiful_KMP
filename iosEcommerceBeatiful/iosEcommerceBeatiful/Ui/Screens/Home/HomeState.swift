//
//  HomeState.swift
//  iosEcommerceBeatiful
//
//  Created by kenjimaeda on 12/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
class HomeState: ObservableObject {
	@Published var loadingState: LoadingState = .none
    private var viewModel  = HomeViewModel()
	var productsByCategory: [AmazonProductCategoryModel] = []

    func getProducts(id: String) async {
        loadingState = .loading
        viewModel.getProductByCategory(categoryId: id, differenceMinutes: 15)
        for await result in viewModel.listProductsCategory {

            productsByCategory = result
            loadingState = .none

        }
    }
}
