package thuy.flickr

import dagger.Module
import dagger.android.ContributesAndroidInjector
import thuy.flickr.photodetails.PhotoDetailsFragment
import thuy.flickr.recent.RecentPhotosFragment

@Module
abstract class AndroidBindingModule {
  @ContributesAndroidInjector(modules = arrayOf(
      PhotoRepositoryModule::class
  ))
  abstract fun getRecentPhotosFragment(): RecentPhotosFragment

  @ContributesAndroidInjector
  abstract fun getPhotoDetailsFragment(): PhotoDetailsFragment
}
