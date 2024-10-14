//
//  RowItemCard.swift
//  iosEcommerceBeatiful
//
//  Created by kenjimaeda on 12/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RowItemCard: View {
	let product: AmazonResultSerialization
	
	var body: some View {
		VStack {
			VStack(alignment: .trailing) {
				Image(systemName:  "heart")
					.foregroundStyle(Colors.white)
					.padding(3)
					.background(
						Circle()
							.fill(Colors.gray)
					)
				if let images = product.imageUrls {
					
					if let imagesUrl = images as? [String] {
						AsyncImage(url: URL(string: imagesUrl.first!)) { image in
							
							image
								.resizable()
								.frame(width: 120,height: 150)
								.scaledToFill()
							
						}placeholder: {
							Image("image_placeholder")
								.resizable()
								.frame(width: 120,height: 150)
								.scaledToFill()
							
						}
					}
					
				}
				
			}
			.padding(5)
			.background(
				RoundedRectangle(cornerRadius: 10)
					.fill(Colors.white)
			)
			VStack(alignment: .leading) {
				Text(product.title)
					.font(.custom(FontsApp.openSansLight, size: 17))
					.fontWeight(.light)
					.lineLimit(3)
					.fixedSize(horizontal: false, vertical: true)
				Text(product.price)
					.font(Font.custom(FontsApp.openSansBold, size: 17))
					.foregroundStyle(Colors.black)
				
			}
			
		}
		.frame(width: 130)
		
	}
}

#Preview {
	RowItemCard(product: AmazonResultSerialization(asin: "", imageUrls: [
		"https://github.com/kenjimaeda54.png"
	] , price: " R$34,24", title: "Camiseta", brand: "Lacotes", url: "https://github.com/kenjimaeda54.png", rating: 4.3, seller: AmazonSellerSerialization(name:  "Lacoste", logoUrl: "https://github.com/kenjimaeda54.png", rating: 4.3)))
}
