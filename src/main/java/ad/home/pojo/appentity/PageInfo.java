package ad.home.pojo.appentity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageInfo<T> extends Page<T> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer pageType = 1;

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public void setPageNo(Long pageNo) {
        this.setCurrent(pageNo);
    }

    public void setPageSize(Long pageSize) {
        this.setSize(pageSize);
    }

    public Long getTotalRecord() {
        return this.getTotal();
    }

    public Long getTotalPage() {
        return this.getPages();
    }

    public boolean ordered() {
        if(this.ascs() == null && this.descs() == null) {
            return false;
        } else {
            return true;
        }
    }

}
