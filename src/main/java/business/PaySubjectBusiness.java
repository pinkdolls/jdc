package business;

import com.jfinal.plugin.activerecord.Page;
import com.senyoboss.common.SearchCondition;
import com.senyoboss.ioc.Service;
import com.senyoboss.tool.StringUtils;
import model.PaySubject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Tinkpad on 2016/6/13.
 */
@Service
public class PaySubjectBusiness {
    public Page<PaySubject> findByPaginate(Integer pageSize,Integer page,String searchSQL) throws UnsupportedEncodingException {

        SearchCondition sc = new SearchCondition("pay_subject");
        sc.setPage(page);
        sc.setPageSize(pageSize);

        Page<PaySubject> pageList = null;
        String sql = "";
        if (!StringUtils.isNullOrEmpty(sc.getSearchSql())) {
            sql = sc.getSearchSql();
        }
        else {
            sql = "from (SELECT @rownum:=0) r," + sc.getTableName();
        }
        if (sc.getPage() != null && sc.getPageSize() != null) {
            pageList = PaySubject.dao.paginate(sc.getPage(), sc.getPageSize(), "select @rownum:=@rownum+1 AS rownum," + sc.getTableName() + ".*", sql + " " + searchSQL + " order by id desc");
        }
        return pageList;
    }

    /**
     * 校验保存后的支出科目名称是否唯一
     * @param subject_name 要判断的支出科目名称
     * @return Boolean false数据库中已存在相同的支出科目名称，true数据库中不存在此支出科目名称
     */
    public Boolean checkNameSave (String subject_name) throws Exception{
        String sql = "select id from pay_subject where del_flg = 1 and subject_name ='"+ subject_name +"'";
        List<PaySubject> list = PaySubject.dao.find(sql);
        if(list.size()>0){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 校验编辑后的支出科目名称是否唯一
     * @param subject_name 要判断的支出科目名称
     * @return Boolean false数据库中已存在相同的支出科目名称，true数据库中不存在此支出科目名称
     */
    public Boolean checkNameUpdate (String subject_name,String id) throws Exception{
        String sql = "select * from pay_subject where del_flg = 1 and id <> '"+id+"' and subject_name ='"+ subject_name +"'";
        List<PaySubject> list = PaySubject.dao.find(sql);
        if(list.size()>0){
            return false;
        }else{
            return true;
        }
    }

    /**
     *  查询用户真实名
     */
    public List<PaySubject> getUserAll()  throws UnsupportedEncodingException {
        String sql = "select * from sys_user";
        List<PaySubject> list = PaySubject.dao.find(sql);
        return list;
    }


}
