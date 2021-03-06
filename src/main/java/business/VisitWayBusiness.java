package business;

import com.jfinal.plugin.activerecord.Page;
import com.senyoboss.common.SearchCondition;
import com.senyoboss.ioc.Service;
import com.senyoboss.tool.StringUtils;
import model.VisitWay;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Tinkpad on 2016/6/13.
 */
@Service
public class VisitWayBusiness {
    public Page<VisitWay> findByPaginate(Integer pageSize,Integer page,String searchSQL) throws UnsupportedEncodingException {

        SearchCondition sc = new SearchCondition("visit_way");
        sc.setPage(page);
        sc.setPageSize(pageSize);

        Page<VisitWay> pageList = null;
        String sql = "";
        if (!StringUtils.isNullOrEmpty(sc.getSearchSql())) {
            sql = sc.getSearchSql();
        }
        else {
            sql = "from (SELECT @rownum:=0) r," + sc.getTableName();
        }
        if (sc.getPage() != null && sc.getPageSize() != null) {
            pageList = VisitWay.dao.paginate(sc.getPage(), sc.getPageSize(), "select @rownum:=@rownum+1 AS rownum," + sc.getTableName() + ".*", sql + " " + searchSQL + " order by id desc");
        }
        return pageList;
    }
    /**
     * 校验保存后的到访途径类型名称是否唯一
     * @param way_name 要判断的到访途径类型名称
     * @return Boolean false数据库中已存在相同的到访途径类型名称，true数据库中不存在此到访途径类型名称
     */
    public Boolean checkNameSave (String way_name) throws Exception{
        String sql = "select id from visit_way where del_flg = 1 and way_name ='"+ way_name +"'";
        List<VisitWay> list = VisitWay.dao.find(sql);
        if(list.size()>0){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 校验编辑后的到访途径类型名称是否唯一
     * @param way_name 要判断的到访途径类型名称
     * @return Boolean false数据库中已存在相同的到访途径类型名称，true数据库中不存在此到访途径类型名称
     */
    public Boolean checkNameUpdate (String way_name,String id) throws Exception{
        String sql = "select * from visit_way where del_flg = 1 and id <> '"+id+"' and way_name ='"+ way_name +"'";
        List<VisitWay> list = VisitWay.dao.find(sql);
        if(list.size()>0){
            return false;
        }else{
            return true;
        }
    }

    /**
     *  查询用户真实名
     */
    public List<VisitWay> getUserAll()  throws UnsupportedEncodingException {
        String sql = "select * from sys_user";
        List<VisitWay> list = VisitWay.dao.find(sql);
        return list;
    }

}
