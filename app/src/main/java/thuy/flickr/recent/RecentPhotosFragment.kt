package thuy.flickr.recent

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import me.tatarka.bindingcollectionadapter2.ItemBinding
import thuy.flickr.BR
import thuy.flickr.R
import thuy.flickr.databinding.RecentPhotosBinding
import javax.inject.Inject

class RecentPhotosFragment : DaggerFragment() {
  @Inject lateinit var viewModel: RecentPhotosViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true

    viewModel.loadPhotos()
    viewModel.onErrorLoadingPhotos.subscribe {
      view?.let {
        Snackbar
            .make(it, R.string.cannot_load_photos, Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) { viewModel.loadPhotos() }
            .show()
      }
    }
  }

  override fun onCreateView(
      inflater: LayoutInflater?,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? = layoutInflater?.inflate(R.layout.recent_photos, container, false)

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    val binding = RecentPhotosBinding.bind(view)
    binding.swipeRefreshLayout.isEnabled = false
    binding.toolbar.inflateMenu(R.menu.recent_photos)

    binding.photoItemBinding = ItemBinding.of<PhotoViewModel>(BR.viewModel, R.layout.photo)
    binding.viewModel = viewModel
  }

  override fun onDestroy() {
    viewModel.onCleared()
    super.onDestroy()
  }
}
