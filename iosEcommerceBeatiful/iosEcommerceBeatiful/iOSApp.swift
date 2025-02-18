import SwiftUI
import shared

@available(iOS 17.0, *)
@main
struct iOSApp: App {
    let arguments = ProcessInfo.processInfo.environment["ENV"]

	init() {
        CommonModuleKt.doInitKoin(isTesting: (arguments  == "TEST"))
	}
	
	var body: some Scene {
		WindowGroup {
			TabCustomNavigation()
		}
	}
}
