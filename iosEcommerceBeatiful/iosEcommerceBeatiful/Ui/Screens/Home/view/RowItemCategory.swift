//
//  RowItemCategory.swift
//  iosEcommerceBeatiful
//
//  Created by kenjimaeda on 15/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RowItemCategory: View {
	let item: CategoryMap
	var body: some View {
		VStack {
			Image(item.icon)
				.resizable()
				.frame(width: 30,height: 30)
				.padding(.all,3)
				.background(
					Circle()
						.fill(Colors.white)
				)
			
			Text(item.name)
				.font(.custom(FontsApp.openSansRegular, size: 13))
				.foregroundStyle(Colors.black)
		}
		
	}
}




