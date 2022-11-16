package com.example.foodinvetoryapp.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.DaoException;

//TODO: add java docs
@Entity
public class FoodProduct {
    @Id (autoincrement = true)
    private Long id;

    @NotNull
    private String name;
    private String barCode;
    private String nutrientScore;
    private Date expireDate;
    private long storageId;

    @ToOne(joinProperty = "storageId")
    private Storage storage;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1611057112)
    private transient FoodProductDao myDao;

    @Generated(hash = 1044116221)
    public FoodProduct(Long id, @NotNull String name, String barCode,
            String nutrientScore, Date expireDate, long storageId) {
        this.id = id;
        this.name = name;
        this.barCode = barCode;
        this.nutrientScore = nutrientScore;
        this.expireDate = expireDate;
        this.storageId = storageId;
    }

    @Generated(hash = 2001997808)
    public FoodProduct() {
    }

    @Generated(hash = 1770166036)
    private transient Long storage__resolvedKey;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return this.barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getNutriScore() {
        return this.nutrientScore;
    }

    public void setNutriScore(String nutriScore) {
        this.nutrientScore = nutriScore;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public long getStorageId() {
        return this.storageId;
    }

    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }

    public String getNutrientScore() {
        return this.nutrientScore;
    }

    public void setNutrientScore(String nutrientScore) {
        this.nutrientScore = nutrientScore;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1723919968)
    public Storage getStorage() {
        long __key = this.storageId;
        if (storage__resolvedKey == null || !storage__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StorageDao targetDao = daoSession.getStorageDao();
            Storage storageNew = targetDao.load(__key);
            synchronized (this) {
                storage = storageNew;
                storage__resolvedKey = __key;
            }
        }
        return storage;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1596245866)
    public void setStorage(@NotNull Storage storage) {
        if (storage == null) {
            throw new DaoException(
                    "To-one property 'storageId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.storage = storage;
            storageId = storage.getId();
            storage__resolvedKey = storageId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2114767783)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFoodProductDao() : null;
    }

}
