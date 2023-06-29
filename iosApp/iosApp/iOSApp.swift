import SwiftUI
import shared

@main
struct iOSApp: App {

    // TODO 23: initialize the SDK, view, and view model and run the project
    let sdk = SpaceXSdk(databaseDriverFactory: DatabaseDriverFactory())
    
	var body: some Scene {
		WindowGroup {
            ContentView(viewModel: .init(sdk: sdk))
		}
	}
}
