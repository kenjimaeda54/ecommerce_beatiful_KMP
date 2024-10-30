//
//  NavigationGraphBottom.swift
//  iosEcommerceBeatiful
//
//  Created by kenjimaeda on 22/10/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 17.0, *)
struct TabCustomNavigation: View {
	@StateObject private var stateTabNavigation = StateNavigationTabView()
	var body: some View {
		TabView(selection: $stateTabNavigation.tagSelected){
			HomeScreen()
				.tabItem {
					if(stateTabNavigation.tagSelected == 0) {
						Image("homeFill")
					}else {
						Image("home")
					}
					
				}
				.tag(0)
			
			CartScreen()
				.tabItem {
					if( stateTabNavigation.tagSelected == 1) {
						Image("cartFill")
					}else {
						Image("cart")
					}
					
				}
				.tag(1)
			
			FavoriteScreen()
				.tabItem {
					if(stateTabNavigation.tagSelected == 2) {
						Image("favoriteFill")
					}else {
						Image("favorite")
					}
				}
				.tag(2)
		  
			ProfilleScreen()
				.tabItem {
					if(stateTabNavigation.tagSelected == 3) {
						Image("profileFill")
					}else {
						Image("profile")
					}
				}
				.tag(3)
			
		}
		.tint(Colors.green)
	}
}

