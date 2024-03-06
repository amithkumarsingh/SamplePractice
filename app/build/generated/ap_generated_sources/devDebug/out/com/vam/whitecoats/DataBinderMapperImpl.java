package com.vam.whitecoats;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.vam.whitecoats.databinding.ActivityCategorySearchBindingImpl;
import com.vam.whitecoats.databinding.ActivityDrugClassBindingImpl;
import com.vam.whitecoats.databinding.ActivityDrugSubClassBindingImpl;
import com.vam.whitecoats.databinding.ActivityDrugsBindingImpl;
import com.vam.whitecoats.databinding.ActivitySubCategoriesListBindingImpl;
import com.vam.whitecoats.databinding.ActivityWebViewBindingImpl;
import com.vam.whitecoats.databinding.CommunityTabDoctorsBindingImpl;
import com.vam.whitecoats.databinding.CommunityTabFeedBindingImpl;
import com.vam.whitecoats.databinding.CommunityTabOrganizationBindingImpl;
import com.vam.whitecoats.databinding.CommunityTabSpotlightBindingImpl;
import com.vam.whitecoats.databinding.DrugSearchActivityBindingImpl;
import com.vam.whitecoats.databinding.FeedsNotofictionRowLayoutBindingImpl;
import com.vam.whitecoats.databinding.FragmentCategoriesRecentTabBindingImpl;
import com.vam.whitecoats.databinding.FragmentCategoriesSpecialityTabBindingImpl;
import com.vam.whitecoats.databinding.FragmentExploreBindingImpl;
import com.vam.whitecoats.databinding.FragmentFeedNotificationBindingImpl;
import com.vam.whitecoats.databinding.KnowledgeTabDrugBindingImpl;
import com.vam.whitecoats.databinding.NoSubCategoryDistributionBindingImpl;
import com.vam.whitecoats.databinding.NoSubCategoryDistributionItemBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCATEGORYSEARCH = 1;

  private static final int LAYOUT_ACTIVITYDRUGCLASS = 2;

  private static final int LAYOUT_ACTIVITYDRUGSUBCLASS = 3;

  private static final int LAYOUT_ACTIVITYDRUGS = 4;

  private static final int LAYOUT_ACTIVITYSUBCATEGORIESLIST = 5;

  private static final int LAYOUT_ACTIVITYWEBVIEW = 6;

  private static final int LAYOUT_COMMUNITYTABDOCTORS = 7;

  private static final int LAYOUT_COMMUNITYTABFEED = 8;

  private static final int LAYOUT_COMMUNITYTABORGANIZATION = 9;

  private static final int LAYOUT_COMMUNITYTABSPOTLIGHT = 10;

  private static final int LAYOUT_DRUGSEARCHACTIVITY = 11;

  private static final int LAYOUT_FEEDSNOTOFICTIONROWLAYOUT = 12;

  private static final int LAYOUT_FRAGMENTCATEGORIESRECENTTAB = 13;

  private static final int LAYOUT_FRAGMENTCATEGORIESSPECIALITYTAB = 14;

  private static final int LAYOUT_FRAGMENTEXPLORE = 15;

  private static final int LAYOUT_FRAGMENTFEEDNOTIFICATION = 16;

  private static final int LAYOUT_KNOWLEDGETABDRUG = 17;

  private static final int LAYOUT_NOSUBCATEGORYDISTRIBUTION = 18;

  private static final int LAYOUT_NOSUBCATEGORYDISTRIBUTIONITEM = 19;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(19);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.activity_category_search, LAYOUT_ACTIVITYCATEGORYSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.activity_drug_class, LAYOUT_ACTIVITYDRUGCLASS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.activity_drug_sub_class, LAYOUT_ACTIVITYDRUGSUBCLASS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.activity_drugs, LAYOUT_ACTIVITYDRUGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.activity_sub_categories_list, LAYOUT_ACTIVITYSUBCATEGORIESLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.activity_web_view, LAYOUT_ACTIVITYWEBVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.community_tab_doctors, LAYOUT_COMMUNITYTABDOCTORS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.community_tab_feed, LAYOUT_COMMUNITYTABFEED);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.community_tab_organization, LAYOUT_COMMUNITYTABORGANIZATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.community_tab_spotlight, LAYOUT_COMMUNITYTABSPOTLIGHT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.drug_search_activity, LAYOUT_DRUGSEARCHACTIVITY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.feeds_notofiction_row_layout, LAYOUT_FEEDSNOTOFICTIONROWLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.fragment_categories_recent_tab, LAYOUT_FRAGMENTCATEGORIESRECENTTAB);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.fragment_categories_speciality_tab, LAYOUT_FRAGMENTCATEGORIESSPECIALITYTAB);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.fragment_explore, LAYOUT_FRAGMENTEXPLORE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.fragment_feed_notification, LAYOUT_FRAGMENTFEEDNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.knowledge_tab_drug, LAYOUT_KNOWLEDGETABDRUG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.no_sub_category_distribution, LAYOUT_NOSUBCATEGORYDISTRIBUTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.vam.whitecoats.R.layout.no_sub_category_distribution_item, LAYOUT_NOSUBCATEGORYDISTRIBUTIONITEM);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCATEGORYSEARCH: {
          if ("layout/activity_category_search_0".equals(tag)) {
            return new ActivityCategorySearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_category_search is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYDRUGCLASS: {
          if ("layout/activity_drug_class_0".equals(tag)) {
            return new ActivityDrugClassBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_drug_class is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYDRUGSUBCLASS: {
          if ("layout/activity_drug_sub_class_0".equals(tag)) {
            return new ActivityDrugSubClassBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_drug_sub_class is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYDRUGS: {
          if ("layout/activity_drugs_0".equals(tag)) {
            return new ActivityDrugsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_drugs is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSUBCATEGORIESLIST: {
          if ("layout/activity_sub_categories_list_0".equals(tag)) {
            return new ActivitySubCategoriesListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_sub_categories_list is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYWEBVIEW: {
          if ("layout/activity_web_view_0".equals(tag)) {
            return new ActivityWebViewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_web_view is invalid. Received: " + tag);
        }
        case  LAYOUT_COMMUNITYTABDOCTORS: {
          if ("layout/community_tab_doctors_0".equals(tag)) {
            return new CommunityTabDoctorsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for community_tab_doctors is invalid. Received: " + tag);
        }
        case  LAYOUT_COMMUNITYTABFEED: {
          if ("layout/community_tab_feed_0".equals(tag)) {
            return new CommunityTabFeedBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for community_tab_feed is invalid. Received: " + tag);
        }
        case  LAYOUT_COMMUNITYTABORGANIZATION: {
          if ("layout/community_tab_organization_0".equals(tag)) {
            return new CommunityTabOrganizationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for community_tab_organization is invalid. Received: " + tag);
        }
        case  LAYOUT_COMMUNITYTABSPOTLIGHT: {
          if ("layout/community_tab_spotlight_0".equals(tag)) {
            return new CommunityTabSpotlightBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for community_tab_spotlight is invalid. Received: " + tag);
        }
        case  LAYOUT_DRUGSEARCHACTIVITY: {
          if ("layout/drug_search_activity_0".equals(tag)) {
            return new DrugSearchActivityBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for drug_search_activity is invalid. Received: " + tag);
        }
        case  LAYOUT_FEEDSNOTOFICTIONROWLAYOUT: {
          if ("layout/feeds_notofiction_row_layout_0".equals(tag)) {
            return new FeedsNotofictionRowLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for feeds_notofiction_row_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCATEGORIESRECENTTAB: {
          if ("layout/fragment_categories_recent_tab_0".equals(tag)) {
            return new FragmentCategoriesRecentTabBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_categories_recent_tab is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCATEGORIESSPECIALITYTAB: {
          if ("layout/fragment_categories_speciality_tab_0".equals(tag)) {
            return new FragmentCategoriesSpecialityTabBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_categories_speciality_tab is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTEXPLORE: {
          if ("layout/fragment_explore_0".equals(tag)) {
            return new FragmentExploreBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_explore is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTFEEDNOTIFICATION: {
          if ("layout/fragment_feed_notification_0".equals(tag)) {
            return new FragmentFeedNotificationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_feed_notification is invalid. Received: " + tag);
        }
        case  LAYOUT_KNOWLEDGETABDRUG: {
          if ("layout/knowledge_tab_drug_0".equals(tag)) {
            return new KnowledgeTabDrugBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for knowledge_tab_drug is invalid. Received: " + tag);
        }
        case  LAYOUT_NOSUBCATEGORYDISTRIBUTION: {
          if ("layout/no_sub_category_distribution_0".equals(tag)) {
            return new NoSubCategoryDistributionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for no_sub_category_distribution is invalid. Received: " + tag);
        }
        case  LAYOUT_NOSUBCATEGORYDISTRIBUTIONITEM: {
          if ("layout/no_sub_category_distribution_item_0".equals(tag)) {
            return new NoSubCategoryDistributionItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for no_sub_category_distribution_item is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(5);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "adapter");
      sKeys.put(2, "itemClickListener");
      sKeys.put(3, "position");
      sKeys.put(4, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(19);

    static {
      sKeys.put("layout/activity_category_search_0", com.vam.whitecoats.R.layout.activity_category_search);
      sKeys.put("layout/activity_drug_class_0", com.vam.whitecoats.R.layout.activity_drug_class);
      sKeys.put("layout/activity_drug_sub_class_0", com.vam.whitecoats.R.layout.activity_drug_sub_class);
      sKeys.put("layout/activity_drugs_0", com.vam.whitecoats.R.layout.activity_drugs);
      sKeys.put("layout/activity_sub_categories_list_0", com.vam.whitecoats.R.layout.activity_sub_categories_list);
      sKeys.put("layout/activity_web_view_0", com.vam.whitecoats.R.layout.activity_web_view);
      sKeys.put("layout/community_tab_doctors_0", com.vam.whitecoats.R.layout.community_tab_doctors);
      sKeys.put("layout/community_tab_feed_0", com.vam.whitecoats.R.layout.community_tab_feed);
      sKeys.put("layout/community_tab_organization_0", com.vam.whitecoats.R.layout.community_tab_organization);
      sKeys.put("layout/community_tab_spotlight_0", com.vam.whitecoats.R.layout.community_tab_spotlight);
      sKeys.put("layout/drug_search_activity_0", com.vam.whitecoats.R.layout.drug_search_activity);
      sKeys.put("layout/feeds_notofiction_row_layout_0", com.vam.whitecoats.R.layout.feeds_notofiction_row_layout);
      sKeys.put("layout/fragment_categories_recent_tab_0", com.vam.whitecoats.R.layout.fragment_categories_recent_tab);
      sKeys.put("layout/fragment_categories_speciality_tab_0", com.vam.whitecoats.R.layout.fragment_categories_speciality_tab);
      sKeys.put("layout/fragment_explore_0", com.vam.whitecoats.R.layout.fragment_explore);
      sKeys.put("layout/fragment_feed_notification_0", com.vam.whitecoats.R.layout.fragment_feed_notification);
      sKeys.put("layout/knowledge_tab_drug_0", com.vam.whitecoats.R.layout.knowledge_tab_drug);
      sKeys.put("layout/no_sub_category_distribution_0", com.vam.whitecoats.R.layout.no_sub_category_distribution);
      sKeys.put("layout/no_sub_category_distribution_item_0", com.vam.whitecoats.R.layout.no_sub_category_distribution_item);
    }
  }
}
