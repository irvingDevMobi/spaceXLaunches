import SwiftUI
import shared

struct ContentView: View {
    // The @ObservedObject property wrapper is used to subscribe to the view model.
    @ObservedObject private(set) var viewModel: ViewModel

	var body: some View {
        NavigationView {
            // TODO 21: Implement the body of the ContentView file and display the list of launches
            listView()
                .navigationBarTitle("SpaceX Launches")
                .navigationBarItems(
                    trailing: Button("Reload") {
                        self.viewModel.loadLaunches(forceReload: true)
                    }
                )
                
        }
	}
    
    private func listView() -> AnyView {
        switch viewModel.launches {
        case .loading:
            return AnyView(Text("Loading..").multilineTextAlignment(.center))
        case .result(let launches):
            return AnyView(List(launches) { launch in
                RocketLaunchRow(rocketLaunch: launch)
            })
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    
    }
}

// TODO 20: create ViewModel class for the ContentView
// The Combine framework connects the view model (ContentView.ViewModel) with the view (ContentView).
extension ContentView {
    enum LoadableLaunches {
        case loading
        case result([RocketLaunch])
        case error(String)
    }
    
    @MainActor
    class ViewModel: ObservableObject {
        let sdk: SpaceXSdk
        @Published var launches = LoadableLaunches.loading
        
        init(sdk: SpaceXSdk) {
            self.sdk = sdk
            self.loadLaunches(forceReload: false)
        }
        
        func loadLaunches(forceReload: Bool) {
            Task {
                do {
                    self.launches = .loading
                    let launches = try await sdk.getLaunches(forceReload: forceReload)
                    self.launches = .result(launches)
                } catch {
                    self.launches = .error(error.localizedDescription)
                }
            }
        }
    }
}

// TODO 22: To make it compile, the RocketLaunch class needs to confirm the Identifiable protocol,
// as it is used as a parameter for initializing the List Swift UIView.
extension RocketLaunch: Identifiable { }
