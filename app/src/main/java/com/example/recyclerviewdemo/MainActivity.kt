package com.example.recyclerviewdemo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewdemo.adapter.StarsAdapter
import com.example.recyclerviewdemo.adapter.TagsAdapter
import com.example.recyclerviewdemo.model.StarModel
import com.example.recyclerviewdemo.model.TagModel
import com.example.recyclerviewdemo.model.TagType
import com.example.recyclerviewdemo.model.TagType.Companion.getTagType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), StarsAdapter.OnItemClickedListener, View.OnClickListener,
    TagsAdapter.OnItemClickListener {

    private val TAG: String = "MainActivityLog"

    private val selectedStarList = ArrayList<StarModel>()
    private val selectedTagTypes = ArrayList<TagType.Tags>()
    private val selectedTags = ArrayList<TagModel>()
    private lateinit var tagAdapter: TagsAdapter
    private lateinit var starsAdapter: StarsAdapter
    private var filteredTagList = ArrayList<TagModel>()
    private var isAllBtnSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnClickMe.setOnClickListener(this)
        btnAll.setOnClickListener(this)

        rvStars.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTags.layoutManager = LinearLayoutManager(this)

        starsAdapter = StarsAdapter(generateStarModel(), this)
        rvStars.adapter = starsAdapter

        tagAdapter = TagsAdapter(filteredTagList, this)
        rvTags.adapter = tagAdapter
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnClickMe -> {
                tvResult.text = "List of selected stars: ${selectedStarList.map { it.starName }.sorted()}\n" +
                        "List of selected tags: ${selectedTags.map { it.tagName }}"
            }
            R.id.btnAll -> {

                if (isAllBtnSelected) {
                    starsAdapter.unselectAllStar()
                    isAllBtnSelected = false
                    filteredTagList.clear()
                } else {
                    isAllBtnSelected = true
                    starsAdapter.selectAllStar()
                    filteredTagList.clear()
                    filteredTagList.addAll(generateTagModel())
                }
                tagAdapter.notifyDataSetChanged()


            }
            else -> {
            }
        }
    }

    override fun onStarItemClicked(starModel: StarModel) {
        if (starModel.isSelected) {
            if (!selectedStarList.contains(starModel)) {
                selectedStarList.add(starModel)
                selectedTagTypes.add(getTagType(starModel.starName.toInt()))
            }
        } else {
            selectedStarList.remove(starModel)
            selectedTagTypes.remove(getTagType(starModel.starName.toInt()))
        }

        onBtnAllEnabled(checkForAllSelected())
        if (!starModel.isSelected) {
            isAllBtnSelected = false
        }

        filteredTagList.clear()
        filteredTagList.addAll(generateTagModel().filter { selectedTagTypes.contains(it.tagType) })

        tagAdapter.notifyDataSetChanged()
    }

    override fun onTagItemClicked(tagModel: TagModel) {
        if (tagModel.isSelected) {
            selectedTags.add(tagModel)
        } else {
            selectedTags.remove(tagModel)
        }
    }

    private fun checkForAllSelected(): Boolean {
        return selectedStarList.size == generateStarModel().size
    }

    private fun onBtnAllEnabled(isEnabled: Boolean) {
        if (isEnabled) {
            btnAll.setBackgroundColor(Color.parseColor("blue"))
        } else {
            btnAll.setBackgroundColor(Color.parseColor("green"))
        }
    }

    private fun generateTagModel(): List<TagModel> {
        val list = ArrayList<TagModel>()

        list.add(TagModel("Service", TagType.Tags.GOOD, false))
        list.add(TagModel("Music", TagType.Tags.NORMAL, false))
        list.add(TagModel("Clean", TagType.Tags.BAD, false))
        list.add(TagModel("Phone", TagType.Tags.BAD, false))
        list.add(TagModel("Mart", TagType.Tags.NORMAL, false))

        return list
    }

    private fun generateStarModel(): List<StarModel> {
        val list = ArrayList<StarModel>()

        list.add(StarModel("1", false))
        list.add(StarModel("2", false))
        list.add(StarModel("3", false))
        list.add(StarModel("4", false))
        list.add(StarModel("5", false))

        return list
    }
}
