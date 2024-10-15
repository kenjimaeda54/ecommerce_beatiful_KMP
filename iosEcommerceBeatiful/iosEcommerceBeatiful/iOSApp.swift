import SwiftUI
import shared

@available(iOS 17.0, *)
@main
struct iOSApp: App {
	
	init() {
		CommonModuleKt.doInitKoin()
	}
	
	var body: some Scene {
		WindowGroup {
			HomeScreen()
		}
	}
}
