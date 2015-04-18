/**
 * 
 */
package de.wollistik.dlna.db;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsToPolymorphic;

/**
 * @author Wolfgang Wedelich-John<wolfgang.wedelich@wollistik.de>
 *
 */
@BelongsToPolymorphic(parents = { MovieMediaInfo.class })
public class MediaItem extends Model {

}
