package fan.akua.exam.activities.main.model

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.drake.brv.BindingAdapter
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.item.ItemBind
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup
import fan.akua.exam.R
import fan.akua.exam.data.MusicInfo
import fan.akua.exam.databinding.ItemGirdBinding
import fan.akua.exam.databinding.ItemTypeGirdBinding
import fan.akua.exam.utils.dp

class GirdModel(override val modelID: Int,override val data: List<MusicInfo>, val rowCount: Int) :
    BaseModel {
    override fun onBind(vh: BindingAdapter.BindingViewHolder) {
        val binding = ItemTypeGirdBinding.bind(vh.itemView)
        binding.title.text = if (rowCount == 1)
            vh.context.resources.getString(R.string.str_daily_recommend)
        else
            vh.context.resources.getString(R.string.str_hot_rank)

        binding.rv.grid(spanCount = rowCount).setup {
            addType<MusicInfo>(R.layout.item_gird)
            onCreate {
                if (rowCount == 1) {
                    val itemGirdBinding = getBinding<ItemGirdBinding>()
                    val layoutParams = itemGirdBinding.parentCardView.layoutParams
                    layoutParams.height = 170.dp(itemView.context).toInt()
                    itemGirdBinding.parentCardView.layoutParams = layoutParams
                }
            }
            onBind {
                val model = getModel<MusicInfo>()
                val itemGirdBinding = getBinding<ItemGirdBinding>()
                itemGirdBinding.musicInfo = model
                Glide.with(itemGirdBinding.img)
                    .load(model.coverUrl)
                    .into(itemGirdBinding.img)
                itemGirdBinding.root.setOnClickListener {
                    Toast.makeText(context, model.musicName, Toast.LENGTH_SHORT).show()
                }
                itemGirdBinding.playButton.setOnClickListener {
                    Toast.makeText(
                        context,
                        "将${model.musicName}添加到音乐列表",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.models = data
        binding.rv.bindingAdapter.setAnimation(AnimationType.SLIDE_BOTTOM)
    }
}