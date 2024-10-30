//
//  HomeScreen.swift
//  iosEcommerceBeatiful
//
//  Created by kenjimaeda on 12/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

@available(iOS 17.0, *)
struct HomeScreen: View {
	@State var searchProduct: String = ""
	@StateObject private var homeState = HomeState()
	@State private var scrollToCategoryIndex = 0
	
	
	var body: some View {
		GeometryReader { geometry in
			VStack {
				VStack {
					ZStack {
						
						if(searchProduct.isEmpty) {
							
							Image(systemName: "magnifyingglass")
								.foregroundStyle(Colors.black.opacity(0.7))
								.padding(.trailing, geometry.size.width * 0.65)
							
							Text("Pesquisar na loja como todo")
								.font(.custom(FontsApp.openSansLight, size: 17))
								.foregroundStyle(Colors.black.opacity(0.7))
								.multilineTextAlignment(.center)
						}
						
						TextField("", text: $searchProduct)
							.padding(.leading,20)
							.font(.custom(FontsApp.openSansRegular, size: 17))
							.foregroundStyle(Colors.black.opacity(0.9))
						
						
					}
					.padding(.vertical,10)
					.background(
						RoundedRectangle(cornerRadius: 10)
							.fill(Colors.white)
					)
				}
				.padding(.top,geometry.safeAreaInsets.top + 60)
				.padding(.bottom,geometry.safeAreaInsets.top + 30)
				.padding(.horizontal,10)
				.background(
					UnevenRoundedRectangle(cornerRadii: .init(
						topLeading: 0, bottomLeading: 10,bottomTrailing: 10,topTrailing: 0)
					)
					.fill(Colors.grayWithe)
					
				)
				Spacer(minLength: 25)
				
				VStack(alignment: .leading){
					Text("Categorias")
						.font(.custom(FontsApp.openSansBold, size: 17))
						.foregroundStyle(Colors.black)
						.padding(.top,15)
						.padding(.horizontal,15)
					
					ScrollViewReader { value in
						List {
							ScrollView(.horizontal)  {
								
								LazyHStack(spacing: 15) {
									ForEach(Array(categoryMap.enumerated()),id: \.1.id) { (index,item) in
										Button {
											withAnimation {
												value.scrollTo(index,anchor: .top)
											}
										} label: {
											RowItemCategory(item:item)
											
										}
										.padding(.trailing,10)
										
									}
								}
								
							}
							.scrollIndicators(.hidden)
							.listRowSeparator(.hidden)
							.listRowInsets(.init(top:0, leading: 10, bottom: 0, trailing: 0))
							.listRowBackground(Color.clear)
						}
						
						.frame(height: geometry.size.height * 0.10)
						.listStyle(.plain)
						List {
							ForEach(Array(homeState.productsByCategory.enumerated()),id: \.1.id) {(index,product) in
								Text(product.name ?? "")
									.font(.custom(FontsApp.openSansBold, size: 17))
									.foregroundStyle(Colors.black)
									.padding(.top,15)
									.id(index)
								if(product.results!.isEmpty) {
									ScrollView(.horizontal) {
										LazyHStack(spacing: 10) {
											ForEach(0..<10) { _ in
												RoundedRectangle(cornerRadius: 10)
													.frame(width: 130, height: 150)
											}
										}
										.redactShimmer(condition: true)
										
									}
								}else {
									ScrollView(.horizontal) {
										LazyHStack(spacing: 10) {
											ForEach(product.results!, id: \.asin) { result in
												if(!result.price.isEmpty){
													RowItemCard(product: result)
														.onAppear {
															//por a lista so aparecer conteudo conforme esta na tela posso fazer assim
															//conteudo nao mostra de uma vez
															scrollToCategoryIndex = index
														}
												}
												
											}
										}
										
									}
									.scrollIndicators(.hidden)
									
								}
								
							}
							.listRowSeparator(.hidden)
							.listRowInsets(.init(top:0, leading: 10, bottom: 0, trailing: 0))
							.listRowBackground(Color.clear)
						}
						.listStyle(.plain)
						.scrollContentBackground(.hidden)
						.scrollIndicators(.hidden)
					}
					
				}
				.padding(.top,15)
				.padding(.bottom,35)
				.background(
					UnevenRoundedRectangle(cornerRadii: .init(topLeading: 10,bottomLeading: 0, bottomTrailing: 0, topTrailing: 10))
						.fill(Colors.grayWithe)
				)
			}
			
		}
		.onAppear {
			UIScrollView.appearance().bounces = false
		}
		.ignoresSafeArea(edges: .all)
		.background(
			Colors.white
		)
		.task {
			categoryMap.forEach { item   in
				Task {
					await homeState.getProduct(id: item.id)
				}
				
			}
		}
		
	}
}


