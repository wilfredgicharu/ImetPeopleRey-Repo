/*
 *
 *  * Copyright (c) 2018 Razeware LLC
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 *  * distribute, sublicense, create a derivative work, and/or sell copies of the
 *  * Software in any work that is designed, intended, or marketed for pedagogical or
 *  * instructional purposes related to programming, coding, application development,
 *  * or information technology.  Permission for such use, copying, modification,
 *  * merger, publication, distribution, sublicensing, creation of derivative works,
 *  * or sale is expressly withheld.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 *
 */

package com.raywenderlich.android.imet.ui.list

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import com.raywenderlich.android.imet.IMetApp
import com.raywenderlich.android.imet.R
import com.raywenderlich.android.imet.data.model.People
import com.raywenderlich.android.imet.ui.add.AddPeopleActivity
import com.raywenderlich.android.imet.ui.details.PeopleDetailsActivity
import kotlinx.android.synthetic.main.fragment_peoples_list.*

/**
 * The Fragment to show people list
 */
class PeoplesListFragment : Fragment(),
    PeoplesListAdapter.OnItemClickListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener {

  private lateinit var searchView: SearchView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }


  //class that observes the data and updates the UI
  override fun onResume() {
    super.onResume()

    val peopleRepository = (activity?.application as IMetApp).getPeopleRepository()
    peopleRepository.getAllPeople().observe(this, Observer { peopleList->
      populatePeopleList(peopleList!!)
    })
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_peoples_list, container, false)
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater?.inflate(R.menu.menu_peoples_list, menu)

    // Initialize Search View
    searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
    searchView.setOnQueryTextListener(this)
    searchView.setOnCloseListener(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // Navigate to add people
    addFab.setOnClickListener {
      val addPeopleIntent = Intent(context, AddPeopleActivity::class.java)
      startActivity(addPeopleIntent)
    }
  }

  /**
   * Callback for searchView text change
   */
  override fun onQueryTextChange(newText: String?) = true

  /**
   * Callback for searchView query submit
   */
  override fun onQueryTextSubmit(query: String?): Boolean {
    return true
  }

  /**
   * Callback for searchView close
   */
  override fun onClose(): Boolean {
    return true
  }

  /**
   * Populates peopleRecyclerView with all people info
   */
  private fun populatePeopleList(peopleList: List<People>) {
    peopleRecyclerView.adapter = PeoplesListAdapter(peopleList, this)
  }

  /**
   * Navigates to people details on item click
   */
  override fun onItemClick(people: People, itemView: View) {
    val detailsIntent = Intent(context, PeopleDetailsActivity::class.java)
    detailsIntent.putExtra(getString(R.string.people_id), people.id)
    startActivity(detailsIntent)
  }

}
