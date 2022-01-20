package james.learning.rickandmortyapi

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import james.learning.rickandmortyapi.api.Result
import james.learning.rickandmortyapi.databinding.ItemsBinding

class ItemsAdapter(private val context: Context, private val results :List<Result>): RecyclerView.Adapter<ItemsAdapter.VH>() {
    class VH(val view: ItemsBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = results[position]
        val currentView = holder.view
        Glide.with(currentView.root)
            .load(currentItem.image)
            .into(currentView.imageView)

        currentView.name.text = currentItem.name
        currentView.gender.text = currentItem.gender
    }

    override fun getItemCount(): Int {
        return results.size
    }
}